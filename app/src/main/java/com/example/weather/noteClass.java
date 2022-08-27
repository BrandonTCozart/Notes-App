package com.example.weather;

import android.media.Image;

public class noteClass {
    String noteText;
    String noteTitle;
    String noteCreationDate;
    Image imageNote;

    public noteClass(String noteText, String noteTitle, String noteCreationDate) {
        this.noteText = noteText;
        this.noteTitle = noteTitle;
        this.noteCreationDate = noteCreationDate;
    }

    public Image getImageNote() {
        return imageNote;
    }

    public void setImageNote(Image imageNote) {
        this.imageNote = imageNote;
    }
}
