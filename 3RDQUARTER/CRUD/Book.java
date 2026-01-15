package MyLibrarySystem;

public class Book {
    private BookID id;
    private Title title;
    private Authors authors;
    private PublishedDate publishedDate;

    public Book(BookID id, Title title, Authors authors, PublishedDate publishedDate) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
    }

    // GETTERS
    public BookID getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public Authors getAuthors() {
        return authors;
    }

    public PublishedDate getPublishedDate() {
        return publishedDate;
    }

    // SETTERS
    public void setId(BookID id) {
        this.id = id;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setAuthors(Authors authors) {
        this.authors = authors;
    }

    public void setPublishedDate(PublishedDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return id.getId() + " | " +
               title.getTitle() + " | " +
               authors.getAuthors() + " | " +
               publishedDate.getDate();
    }
}
