package org.itstep.Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Post {
    private int id = 0;
    private String title = null;
    private String author = null;
    private Date dateTime = null;
    private String text = null;
    private String img = "http://placehold.it/750x300";
    private int draft = 0;
    private final String SQL_DATETIME_PATTERN = "yy-MM-dd HH:mm:ss";

    public Post(int id, String title, String author, String dateTime, String text, int draft) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(SQL_DATETIME_PATTERN);
        this.id = id;
        this.title = title;
        this.author = author;
        this.dateTime = formatter.parse(dateTime);
        this.text = text;
        this.draft = draft;
    }

    public Post(String title, String author, String text, int draft) {
        Calendar calendar = Calendar.getInstance();
        this.title = title;
        this.author = author;
        this.text = text;
        this.draft = draft;
        this.dateTime = calendar.getTime();
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDraft(int draft) {
        this.draft = draft;
    }

    public String getImg() {
        return img;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(SQL_DATETIME_PATTERN);
        return formatter.format(this.dateTime);
    }

    public String getText() {
        return text;
    }

    public String getShortText(int length) {
        String text = this.text;
        if (text.length() > length) {
            return text.substring(0, length).concat("...");
        }
        return text;
    }

    public int getDraft() {
        return draft;
    }

    public String dateToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        return formatter.format(dateTime);
    }

    public String timeToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        return formatter.format(dateTime);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", dateTime=" + dateTime +
                ", text='" + text + '\'' +
                ", img='" + img + '\'' +
                ", draft=" + draft +
                ", SQL_DATETIME_PATTERN='" + SQL_DATETIME_PATTERN + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Post post = new Post("MyTitle", "MyAuthor", "MyText", 0);
//        System.out.println(post);
    }
}
