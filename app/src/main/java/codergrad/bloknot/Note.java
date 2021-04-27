package codergrad.bloknot;

import java.util.Objects;

public class Note {
    private long ID;
    private String title;
    private String content;
    private String date;

    Note(long ID, String title, String content, String date){
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.date = date;
    }
    public long getId(){
        return ID;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public void setContent(){
        this.content = content;
    }
    public void setTitle(){
        this.title = title;
    }
    public String getDate(){
        return date;
    }
}
