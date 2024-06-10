package com.example.myapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Card;
import com.example.myapplication.model.Folder;
import com.example.myapplication.repository.FolderRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNewFolderViewModel extends ViewModel {
    private List<Card> cards = new ArrayList<>();
    private FolderRepository folderRepository;
    private Folder folder = new Folder();

    private MutableLiveData<ArrayList<Folder>> allFolders;

    public AddNewFolderViewModel() {
        folderRepository = new FolderRepository();
        allFolders = new MutableLiveData<>();
    }
    public void addCards(String item1, String item2){
        cards.add((new Card(item1, item2, giveCurrentDay(), giveCurrentMonth(), giveCurrentYear(),  giveCurrentDay(), giveCurrentMonth(), giveCurrentYear(), 0, 0, 0, cards.size())));

    }
    public static int giveCurrentDay() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getDayOfMonth();
    }
    public static int giveCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonthValue();
    }
    public static int giveCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear();
    }
    public void createNewFolder(String title,String username){
        String folderId;
        Date date = new Date();
        folderId = String.valueOf(date.getTime());
        folderId = folderId.substring(folderId.length() - 9);
        folder.setCards(cards);
        folder.setTitle(title);
        folder.setAuthor(username);
        folder.setUid(FirebaseAuth.getInstance().getUid());
        folder.setFolderId(folderId);
        folderRepository.addToFirebase(folderId, folder);


    }
}
