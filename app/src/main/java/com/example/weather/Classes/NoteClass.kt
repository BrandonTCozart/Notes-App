package com.example.weather.Classes

class noteClass {
    var noteText: String? = null
    var noteTitle: String
    var noteCreationDate: String
    var imageNote //refer to address in gallery
            : String? = null
    var Id = 0

    constructor(
        noteText: String?,
        noteTitle: String,
        noteCreationDate: String,
        imageNote: String?
    ) {
        this.noteText = noteText
        this.noteTitle = noteTitle
        this.noteCreationDate = noteCreationDate
        this.imageNote = imageNote
    }

    constructor(noteTitle: String, noteCreationDate: String) {
        this.noteTitle = noteTitle
        this.noteCreationDate = noteCreationDate
    }
}