package com.example.connectserver.domain.preference.service;

import com.example.connectserver.domain.preference.dto.BookmarkRequest;
import com.example.connectserver.domain.preference.entity.BookmarkEntity;
import com.example.connectserver.domain.preference.repository.BookmarkRepository;
import com.example.connectserver.domain.user.entity.UserEntity;
import com.example.connectserver.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkCreateService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    @Transactional
    public void excute(BookmarkRequest request, Long instaId){
        UserEntity user = userRepository.findById(instaId).orElseThrow(() -> new RuntimeException("user not found"));

        BookmarkEntity bookmarkEntity = new BookmarkEntity();
        bookmarkEntity.setUser(user);
        bookmarkEntity.setName(request.getName());
        bookmarkEntity.setCard_id(request.getCard_id());

        bookmarkRepository.save(bookmarkEntity);

    }

}
