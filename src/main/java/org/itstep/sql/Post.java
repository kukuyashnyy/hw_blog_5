package org.itstep.sql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Post {
    private int id = 0;
    private final String title;
    private final String author;
    private final Date dateTime;
    private final String text;
    private  String img = "http://placehold.it/750x300";
    private final int draft;
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
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", date=" + dateToString() +
                ", time=" + timeToString() +
                ", text='" + text + '\'' +
                ", draft=" + draft +
                '}';
    }

    public static void main(String[] args) {
        Post post = new Post("MyTitle", "MyAuthor", "MyText", 0);
//        System.out.println(post);
    }
}
