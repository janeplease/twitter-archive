package com.example.TwitterJavaSDKBasic2.repository;

import com.example.TwitterJavaSDKBasic2.model.BookmarkedTweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface BookmarksRepository extends JpaRepository<BookmarkedTweet, String> {

}