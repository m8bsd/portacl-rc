import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Blog Post Model
class BlogPost {
    private static int nextId = 1;
    private int id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    
    public BlogPost(String title, String content, String author) {
        this.id = nextId++;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }
    
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("\n[Post #%d] %s\nBy: %s | Date: %s\n%s\n%s",
            id, title, author, createdAt.format(formatter), 
            "-".repeat(60), content);
    }
}

// Blog Service
class BlogService {
    private List<BlogPost> posts = new ArrayList<>();
    
    public void createPost(String title, String content, String author) {
        BlogPost post = new BlogPost(title, content, author);
        posts.add(post);
        System.out.println("✓ Post created successfully!");
    }
    
    public void listAllPosts() {
        if (posts.isEmpty()) {
            System.out.println("No posts yet.");
            return;
        }
        System.out.println("\n=== ALL BLOG POSTS ===");
        for (BlogPost post : posts) {
            System.out.println(post);
        }
    }
    
    public void viewPost(int id) {
        BlogPost post = findPostById(id);
        if (post != null) {
            System.out.println(post);
        } else {
            System.out.println("Post not found.");
        }
    }
    
    public void updatePost(int id, String newTitle, String newContent) {
        BlogPost post = findPostById(id);
        if (post != null) {
            post.setTitle(newTitle);
            post.setContent(newContent);
            System.out.println("✓ Post updated successfully!");
        } else {
            System.out.println("Post not found.");
        }
    }
    
    public void deletePost(int id) {
        BlogPost post = findPostById(id);
        if (post != null) {
            posts.remove(post);
            System.out.println("✓ Post deleted successfully!");
        } else {
            System.out.println("Post not found.");
        }
    }
    
    private BlogPost findPostById(int id) {
        return posts.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElse(null);
    }
}

// Main Application
public class SimpleBlog {
    private static Scanner scanner = new Scanner(System.in);
    private static BlogService blogService = new BlogService();
    
    public static void main(String[] args) {
        // Add sample posts
        blogService.createPost("Welcome to My Blog", 
            "This is my first blog post. I'm excited to share my thoughts here!", 
            "John Doe");
        blogService.createPost("Java Programming Tips", 
            "Here are some useful Java tips for beginners...", 
            "Jane Smith");
        
        boolean running = true;
        while (running) {
            showMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    listPosts();
                    break;
                case 2:
                    viewPost();
                    break;
                case 3:
                    createPost();
                    break;
                case 4:
                    updatePost();
                    break;
                case 5:
                    deletePost();
                    break;
                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
    
    private static void showMenu() {
        System.out.println("\n╔════════════════════════════╗");
        System.out.println("║      SIMPLE JAVA BLOG      ║");
        System.out.println("╚════════════════════════════╝");
        System.out.println("1. List all posts");
        System.out.println("2. View a post");
        System.out.println("3. Create new post");
        System.out.println("4. Update a post");
        System.out.println("5. Delete a post");
        System.out.println("6. Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    private static void listPosts() {
        blogService.listAllPosts();
    }
    
    private static void viewPost() {
        System.out.print("Enter post ID: ");
        int id = getIntInput();
        blogService.viewPost(id);
    }
    
    private static void createPost() {
        scanner.nextLine(); // consume newline
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter content: ");
        String content = scanner.nextLine();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        blogService.createPost(title, content, author);
    }
    
    private static void updatePost() {
        System.out.print("Enter post ID to update: ");
        int id = getIntInput();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new content: ");
        String content = scanner.nextLine();
        blogService.updatePost(id, title, content);
    }
    
    private static void deletePost() {
        System.out.print("Enter post ID to delete: ");
        int id = getIntInput();
        blogService.deletePost(id);
    }
    
    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid input. Enter a number: ");
        }
        return scanner.nextInt();
    }
}