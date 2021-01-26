package org.itstep.Dao.Service;

import org.itstep.Classes.Post;
import org.itstep.Dao.Impl.GenericDao;
import org.itstep.Dao.Impl.PostDaoMySqlImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PostService {
    private GenericDao<Post, Integer> service;

    public void setService(GenericDao<Post, Integer> service) {
        this.service = service;
    }

    public static void main(String[] args) throws IOException, SQLException {
        PostService postService = new PostService();
        postService.setService(new PostDaoMySqlImpl());
        postService.service.save(new Post("MyTitle1", "user", "MyText", 0));
        postService.service.save(new Post("MyTitle2", "user", "MyText", 0));
        PrintPosts(postService.service.findAll());
        Post post = postService.service.findById(2);
        post.setTitle("MyTitleChanged");
        postService.service.update(post);
        PrintPosts(postService.service.findAll());
        postService.service.delete(2);
        PrintPosts(postService.service.findAll());
    }

    private static void PrintPosts(List<Post> posts) {
        for (Post post : posts) {
            System.out.println(post.toString());
        }
    }
}
