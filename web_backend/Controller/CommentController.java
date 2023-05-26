package com.example.web_backend.Controller;

import com.example.web_backend.entity.Book;
import com.example.web_backend.entity.Comment;
import com.example.web_backend.entity.User;
import com.example.web_backend.mapper.BookMapper;
import com.example.web_backend.mapper.CommentMapper;
import com.example.web_backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/book/view")
    public List<Comment> commentLoad(@RequestParam int bookId){
        return commentMapper.selectByBook(bookId);
    }

    @PostMapping("/book/comment")
    public void userComment(@RequestParam int bookId, @RequestParam String username,@RequestParam String commentMes){
        User user = userMapper.selectByUsername(username);
        Book book = bookMapper.selectById(bookId);
        Comment comment = new Comment();
        comment.setUid(user.getId());
        comment.setBookId(book.getId());
        comment.setComment(commentMes);
        commentMapper.insert(comment);
    }

    @GetMapping("/book/personalComment")
    public List<Comment> personalComment(@RequestParam String username){
        User user = userMapper.selectByUsername(username);
        return commentMapper.selectByUid(user.getId());
    }

}
