package codergrad.bloknot;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Note implements Parcelable {
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

    protected Note(Parcel in) {
        ID = in.readLong();
        title = in.readString();
        content = in.readString();
        date = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public long getId(){
        return ID;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getDate(){
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(ID);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(date);
    }

}
