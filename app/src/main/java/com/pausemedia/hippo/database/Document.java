package com.pausemedia.hippo.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "document_table")
public class Document {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "commentary")
    private String commentary;

    @ColumnInfo(name = "date")
    private String inputDate;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }
}
