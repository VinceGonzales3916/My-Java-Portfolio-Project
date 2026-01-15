package MyLibrarySystem;

public class book {
    private bookID id;
    private title title;
    private authors authors;
    private publishedDate publishedDate;

    public book(bookID id, title title, authors authors, publishedDate publishedDate) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
    }

    // GETTERS
    public bookID getId() {
        return id;
    }

    public title getTitle() {
        return title;
    }

    public authors getAuthors() {
        return authors;
    }

    public publishedDate getPublishedDate() {
        return publishedDate;
    }

    // SETTERS
    public void setId(bookID id) {
        this.id = id;
    }

    public void setTitle(title title) {
        this.title = title;
    }

    public void setAuthors(authors authors) {
        this.authors = authors;
    }

    public void setPublishedDate(publishedDate publishedDate) {
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
