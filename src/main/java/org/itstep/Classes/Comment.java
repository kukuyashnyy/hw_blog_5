package org.itstep.Classes;

public class Comment {
    private final int id;
    private final int post_id;
    private final String authorName;
    private final String text;

    public Comment(int id, int post_id, String authorName, String text) {
        this.id = id;
        this.post_id = post_id;
        this.authorName = authorName;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public int getPost_id() {
        return post_id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", post_id=" + post_id +
                ", authorName='" + authorName + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
