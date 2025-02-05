package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public void addFolders(List<String> folderNames, User user) {
        List<Folder> existFolders = folderRepository.findAllByUserAndNameIn(user, folderNames);
        List<Folder> Folders = new ArrayList<>();

            for (String folderName : folderNames) {
                if (!isExistFolder(folderName,existFolders)) {
                    Folder Folder = new Folder(folderName, user);
                    Folders.add(Folder);
                }else{
                    throw  new IllegalArgumentException("폴더명이 중복되었습니다.");
                }
        }

        folderRepository.saveAll(Folders);
    }

    private boolean isExistFolder(String folderName, List<Folder> existFolders) {
        for (Folder existFolder : existFolders) {
            if (existFolder.getName().equals(folderName)) {
                return true;
            }
        }
        return false;
    }

    public List<FolderResponseDto> getFolders(User user) {

         List<Folder> folders = folderRepository.findAllByUser(user);
         List<FolderResponseDto> folderResponseDtoList = new ArrayList<>();

        for (Folder folder : folders) {
            folderResponseDtoList.add(new FolderResponseDto(folder));
        }
        return folderResponseDtoList;
    }
}
