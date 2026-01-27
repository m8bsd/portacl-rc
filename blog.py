#!/usr/bin/env python3
"""
Simple Blog Application with CRUD operations
No external frameworks - uses only Python standard library
"""

import json
import os
from http.server import HTTPServer, BaseHTTPRequestHandler
from urllib.parse import parse_qs, urlparse
from datetime import datetime

# Data storage file
DATA_FILE = 'blog_posts.json'

class BlogPost:
    """Represents a blog post"""
    def __init__(self, id, title, content, author, created_at=None):
        self.id = id
        self.title = title
        self.content = content
        self.author = author
        self.created_at = created_at or datetime.now().isoformat()
    
    def to_dict(self):
        return {
            'id': self.id,
            'title': self.title,
            'content': self.content,
            'author': self.author,
            'created_at': self.created_at
        }

class BlogStorage:
    """Handles data persistence"""
    @staticmethod
    def load_posts():
        if os.path.exists(DATA_FILE):
            with open(DATA_FILE, 'r') as f:
                return json.load(f)
        return []
    
    @staticmethod
    def save_posts(posts):
        with open(DATA_FILE, 'w') as f:
            json.dump(posts, f, indent=2)
    
    @staticmethod
    def get_next_id():
        posts = BlogStorage.load_posts()
        if not posts:
            return 1
        return max(post['id'] for post in posts) + 1

class BlogHandler(BaseHTTPRequestHandler):
    """HTTP request handler for blog operations"""
    
    def _set_headers(self, status=200, content_type='text/html'):
        self.send_response(status)
        self.send_header('Content-type', content_type)
        self.end_headers()
    
    def _get_post_data(self):
        content_length = int(self.headers['Content-Length'])
        post_data = self.rfile.read(content_length).decode('utf-8')
        return parse_qs(post_data)
    
    def do_GET(self):
        """Handle GET requests - List all posts or view single post"""
        parsed_path = urlparse(self.path)
        path = parsed_path.path
        
        if path == '/' or path == '/posts':
            self._list_posts()
        elif path.startswith('/posts/'):
            post_id = path.split('/')[-1]
            if post_id.isdigit():
                self._view_post(int(post_id))
            else:
                self._not_found()
        elif path == '/new':
            self._new_post_form()
        elif path.startswith('/edit/'):
            post_id = path.split('/')[-1]
            if post_id.isdigit():
                self._edit_post_form(int(post_id))
            else:
                self._not_found()
        else:
            self._not_found()
    
    def do_POST(self):
        """Handle POST requests - Create new post"""
        if self.path == '/posts':
            self._create_post()
        else:
            self._not_found()
    
    def do_PUT(self):
        """Handle PUT requests - Update existing post"""
        if self.path.startswith('/posts/'):
            post_id = self.path.split('/')[-1]
            if post_id.isdigit():
                self._update_post(int(post_id))
            else:
                self._not_found()
        else:
            self._not_found()
    
    def do_DELETE(self):
        """Handle DELETE requests - Delete a post"""
        if self.path.startswith('/posts/'):
            post_id = self.path.split('/')[-1]
            if post_id.isdigit():
                self._delete_post(int(post_id))
            else:
                self._not_found()
        else:
            self._not_found()
    
    def _list_posts(self):
        """Display all blog posts"""
        posts = BlogStorage.load_posts()
        
        html = """
        <!DOCTYPE html>
        <html>
        <head>
            <title>My Blog</title>
            <style>
                body { font-family: Arial, sans-serif; max-width: 800px; margin: 50px auto; padding: 20px; }
                h1 { color: #333; }
                .post { border: 1px solid #ddd; padding: 20px; margin: 20px 0; border-radius: 5px; }
                .post h2 { margin-top: 0; color: #2c3e50; }
                .post-meta { color: #7f8c8d; font-size: 0.9em; margin-bottom: 10px; }
                .post-content { margin: 15px 0; line-height: 1.6; }
                .actions { margin-top: 10px; }
                button, .btn { 
                    padding: 8px 15px; 
                    margin-right: 10px; 
                    cursor: pointer; 
                    border: none; 
                    border-radius: 3px;
                    text-decoration: none;
                    display: inline-block;
                }
                .btn-primary { background: #3498db; color: white; }
                .btn-danger { background: #e74c3c; color: white; }
                .btn-success { background: #2ecc71; color: white; }
                .btn-secondary { background: #95a5a6; color: white; }
                .empty { text-align: center; color: #7f8c8d; padding: 40px; }
            </style>
        </head>
        <body>
            <h1>My Blog</h1>
            <a href="/new" class="btn btn-success">Create New Post</a>
            <hr>
        """
        
        if posts:
            for post in sorted(posts, key=lambda x: x['created_at'], reverse=True):
                html += f"""
                <div class="post">
                    <h2>{post['title']}</h2>
                    <div class="post-meta">
                        By {post['author']} | {post['created_at'][:10]}
                    </div>
                    <div class="post-content">{post['content'][:200]}{'...' if len(post['content']) > 200 else ''}</div>
                    <div class="actions">
                        <a href="/posts/{post['id']}" class="btn btn-primary">View Full Post</a>
                        <a href="/edit/{post['id']}" class="btn btn-secondary">Edit</a>
                        <button onclick="deletePost({post['id']})" class="btn btn-danger">Delete</button>
                    </div>
                </div>
                """
        else:
            html += '<div class="empty"><p>No posts yet. Create your first post!</p></div>'
        
        html += """
            <script>
                function deletePost(id) {
                    if (confirm('Are you sure you want to delete this post?')) {
                        fetch('/posts/' + id, { method: 'DELETE' })
                            .then(() => location.reload())
                            .catch(err => alert('Error deleting post'));
                    }
                }
            </script>
        </body>
        </html>
        """
        
        self._set_headers()
        self.wfile.write(html.encode())
    
    def _view_post(self, post_id):
        """Display a single post"""
        posts = BlogStorage.load_posts()
        post = next((p for p in posts if p['id'] == post_id), None)
        
        if not post:
            self._not_found()
            return
        
        html = f"""
        <!DOCTYPE html>
        <html>
        <head>
            <title>{post['title']}</title>
            <style>
                body {{ font-family: Arial, sans-serif; max-width: 800px; margin: 50px auto; padding: 20px; }}
                h1 {{ color: #2c3e50; }}
                .post-meta {{ color: #7f8c8d; margin: 20px 0; }}
                .post-content {{ line-height: 1.6; margin: 30px 0; }}
                .actions {{ margin-top: 30px; }}
                .btn {{ 
                    padding: 10px 20px; 
                    margin-right: 10px; 
                    cursor: pointer; 
                    border: none; 
                    border-radius: 3px;
                    text-decoration: none;
                    display: inline-block;
                }}
                .btn-primary {{ background: #3498db; color: white; }}
                .btn-secondary {{ background: #95a5a6; color: white; }}
                .btn-danger {{ background: #e74c3c; color: white; }}
            </style>
        </head>
        <body>
            <h1>{post['title']}</h1>
            <div class="post-meta">
                By {post['author']} | {post['created_at'][:10]}
            </div>
            <div class="post-content">{post['content']}</div>
            <div class="actions">
                <a href="/" class="btn btn-secondary">Back to All Posts</a>
                <a href="/edit/{post['id']}" class="btn btn-primary">Edit</a>
                <button onclick="deletePost()" class="btn btn-danger">Delete</button>
            </div>
            <script>
                function deletePost() {{
                    if (confirm('Are you sure you want to delete this post?')) {{
                        fetch('/posts/{post['id']}', {{ method: 'DELETE' }})
                            .then(() => location.href = '/')
                            .catch(err => alert('Error deleting post'));
                    }}
                }}
            </script>
        </body>
        </html>
        """
        
        self._set_headers()
        self.wfile.write(html.encode())
    
    def _new_post_form(self):
        """Display form to create a new post"""
        html = """
        <!DOCTYPE html>
        <html>
        <head>
            <title>New Post</title>
            <style>
                body { font-family: Arial, sans-serif; max-width: 800px; margin: 50px auto; padding: 20px; }
                h1 { color: #333; }
                form { margin-top: 30px; }
                label { display: block; margin-top: 20px; font-weight: bold; color: #2c3e50; }
                input[type="text"], textarea { 
                    width: 100%; 
                    padding: 10px; 
                    margin-top: 5px; 
                    border: 1px solid #ddd; 
                    border-radius: 3px;
                    box-sizing: border-box;
                    font-family: Arial, sans-serif;
                }
                textarea { min-height: 200px; }
                .btn { 
                    padding: 10px 20px; 
                    margin-top: 20px;
                    margin-right: 10px;
                    cursor: pointer; 
                    border: none; 
                    border-radius: 3px;
                }
                .btn-success { background: #2ecc71; color: white; }
                .btn-secondary { background: #95a5a6; color: white; text-decoration: none; display: inline-block; }
            </style>
        </head>
        <body>
            <h1>Create New Post</h1>
            <form id="postForm">
                <label>Title:</label>
                <input type="text" name="title" required>
                
                <label>Author:</label>
                <input type="text" name="author" required>
                
                <label>Content:</label>
                <textarea name="content" required></textarea>
                
                <button type="submit" class="btn btn-success">Create Post</button>
                <a href="/" class="btn btn-secondary">Cancel</a>
            </form>
            
            <script>
                document.getElementById('postForm').onsubmit = function(e) {
                    e.preventDefault();
                    const formData = new FormData(e.target);
                    const data = new URLSearchParams(formData);
                    
                    fetch('/posts', {
                        method: 'POST',
                        body: data
                    })
                    .then(() => location.href = '/')
                    .catch(err => alert('Error creating post'));
                };
            </script>
        </body>
        </html>
        """
        
        self._set_headers()
        self.wfile.write(html.encode())
    
    def _edit_post_form(self, post_id):
        """Display form to edit an existing post"""
        posts = BlogStorage.load_posts()
        post = next((p for p in posts if p['id'] == post_id), None)
        
        if not post:
            self._not_found()
            return
        
        html = f"""
        <!DOCTYPE html>
        <html>
        <head>
            <title>Edit Post</title>
            <style>
                body {{ font-family: Arial, sans-serif; max-width: 800px; margin: 50px auto; padding: 20px; }}
                h1 {{ color: #333; }}
                form {{ margin-top: 30px; }}
                label {{ display: block; margin-top: 20px; font-weight: bold; color: #2c3e50; }}
                input[type="text"], textarea {{ 
                    width: 100%; 
                    padding: 10px; 
                    margin-top: 5px; 
                    border: 1px solid #ddd; 
                    border-radius: 3px;
                    box-sizing: border-box;
                    font-family: Arial, sans-serif;
                }}
                textarea {{ min-height: 200px; }}
                .btn {{ 
                    padding: 10px 20px; 
                    margin-top: 20px;
                    margin-right: 10px;
                    cursor: pointer; 
                    border: none; 
                    border-radius: 3px;
                }}
                .btn-primary {{ background: #3498db; color: white; }}
                .btn-secondary {{ background: #95a5a6; color: white; text-decoration: none; display: inline-block; }}
            </style>
        </head>
        <body>
            <h1>Edit Post</h1>
            <form id="postForm">
                <label>Title:</label>
                <input type="text" name="title" value="{post['title']}" required>
                
                <label>Author:</label>
                <input type="text" name="author" value="{post['author']}" required>
                
                <label>Content:</label>
                <textarea name="content" required>{post['content']}</textarea>
                
                <button type="submit" class="btn btn-primary">Update Post</button>
                <a href="/posts/{post['id']}" class="btn btn-secondary">Cancel</a>
            </form>
            
            <script>
                document.getElementById('postForm').onsubmit = function(e) {{
                    e.preventDefault();
                    const formData = new FormData(e.target);
                    const data = new URLSearchParams(formData);
                    
                    fetch('/posts/{post['id']}', {{
                        method: 'PUT',
                        body: data
                    }})
                    .then(() => location.href = '/posts/{post['id']}')
                    .catch(err => alert('Error updating post'));
                }};
            </script>
        </body>
        </html>
        """
        
        self._set_headers()
        self.wfile.write(html.encode())
    
    def _create_post(self):
        """Create a new blog post"""
        data = self._get_post_data()
        
        post = BlogPost(
            id=BlogStorage.get_next_id(),
            title=data['title'][0],
            content=data['content'][0],
            author=data['author'][0]
        )
        
        posts = BlogStorage.load_posts()
        posts.append(post.to_dict())
        BlogStorage.save_posts(posts)
        
        self._set_headers(201)
        self.wfile.write(b'Post created')
    
    def _update_post(self, post_id):
        """Update an existing blog post"""
        data = self._get_post_data()
        posts = BlogStorage.load_posts()
        
        for i, post in enumerate(posts):
            if post['id'] == post_id:
                posts[i]['title'] = data['title'][0]
                posts[i]['content'] = data['content'][0]
                posts[i]['author'] = data['author'][0]
                BlogStorage.save_posts(posts)
                self._set_headers()
                self.wfile.write(b'Post updated')
                return
        
        self._not_found()
    
    def _delete_post(self, post_id):
        """Delete a blog post"""
        posts = BlogStorage.load_posts()
        posts = [p for p in posts if p['id'] != post_id]
        BlogStorage.save_posts(posts)
        
        self._set_headers()
        self.wfile.write(b'Post deleted')
    
    def _not_found(self):
        """404 error page"""
        html = """
        <!DOCTYPE html>
        <html>
        <head>
            <title>404 Not Found</title>
            <style>
                body { font-family: Arial, sans-serif; max-width: 800px; margin: 50px auto; padding: 20px; text-align: center; }
                h1 { color: #e74c3c; }
                a { color: #3498db; text-decoration: none; }
            </style>
        </head>
        <body>
            <h1>404 - Not Found</h1>
            <p>The page you're looking for doesn't exist.</p>
            <a href="/">Go back to home</a>
        </body>
        </html>
        """
        
        self._set_headers(404)
        self.wfile.write(html.encode())
    
    def log_message(self, format, *args):
        """Custom log message format"""
        print(f"{self.address_string()} - {format % args}")

def run_server(port=8000):
    """Start the blog server"""
    server_address = ('', port)
    httpd = HTTPServer(server_address, BlogHandler)
    print(f"Blog server running on http://localhost:{port}")
    print("Press Ctrl+C to stop the server")
    print("\nAvailable routes:")
    print("  GET  /           - List all posts")
    print("  GET  /posts/:id  - View single post")
    print("  GET  /new        - New post form")
    print("  GET  /edit/:id   - Edit post form")
    print("  POST /posts      - Create new post")
    print("  PUT  /posts/:id  - Update post")
    print("  DELETE /posts/:id - Delete post")
    
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        print("\nShutting down server...")
        httpd.shutdown()

if __name__ == '__main__':
    run_server()
