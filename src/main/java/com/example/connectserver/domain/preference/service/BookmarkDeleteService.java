package com.example.connectserver.domain.preference.service;

import com.example.connectserver.domain.preference.dto.BookmarkDeleteRequest;
import com.example.connectserver.domain.preference.entity.BookmarkEntity;
import com.example.connectserver.domain.preference.repository.BookmarkRepository;
import com.example.connectserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkDeleteService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    @Transactional
    public void excute(BookmarkDeleteRequest request, Long instaId) {
        BookmarkEntity bookmarkEntity = bookmarkRepository.findByBookmarkId(request.getBookmarkId()).orElseThrow(()-> new RuntimeException("no exist bookmark")
        );

        if(bookmarkEntity.getUser().equals(userRepository.findByInstagramId(instaId))){
            bookmarkRepository.delete(bookmarkEntity);
        }else
            throw new RuntimeException("user is not instagram");

    }
}
