# Simple Python Blog with CRUD Operations

A minimalist blog application built with Python's standard library - no frameworks required!

## Features

- **Create** new blog posts
- **Read** all posts or view individual posts
- **Update** existing posts
- **Delete** posts
- Simple web interface
- JSON-based data persistence
- No external dependencies

## Requirements

- Python 3.6 or higher
- No additional packages needed!

## How to Run

1. Make the script executable (optional):
   ```bash
   chmod +x blog.py
   ```

2. Run the server:
   ```bash
   python3 blog.py
   ```

3. Open your browser and go to:
   ```
   http://localhost:8000
   ```

## API Endpoints

- `GET /` - List all blog posts
- `GET /posts/:id` - View a specific post
- `GET /new` - Display form to create new post
- `GET /edit/:id` - Display form to edit post
- `POST /posts` - Create a new post
- `PUT /posts/:id` - Update an existing post
- `DELETE /posts/:id` - Delete a post

## Data Storage

Posts are stored in `blog_posts.json` in the same directory as the script. The file is created automatically when you create your first post.

## Usage Examples

### Create a Post
1. Click "Create New Post" button
2. Fill in the title, author, and content
3. Click "Create Post"

### View a Post
- Click "View Full Post" on any post from the main page

### Edit a Post
1. Click "Edit" on any post
2. Modify the fields
3. Click "Update Post"

### Delete a Post
- Click "Delete" on any post and confirm the action

## Customization

You can customize the server port by modifying the last line in `blog.py`:

```python
if __name__ == '__main__':
    run_server(8080)  # Change port here
```

## Technical Details

- Built with `http.server` module (Python standard library)
- Uses JSON for data persistence
- RESTful API design
- Client-side JavaScript for dynamic interactions
- No database required

## Stopping the Server

Press `Ctrl+C` in the terminal to stop the server.

## Notes

- This is a simple implementation for learning purposes
- Not suitable for production use without additional security measures
- Data is stored in a single JSON file
- No authentication or authorization implemented
