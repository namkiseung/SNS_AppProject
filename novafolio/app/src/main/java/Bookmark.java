import android.util.Log;

/**
 * Created by namki on 2018-03-14.
 */

public class Bookmark {
    private String title;
    private String url;

    public Bookmark(String url) {
        Log.d("Bookmark접속", "Bookmark(String url)생성자 호출 this.title =="+this.title);
        Log.d("Bookmark접속", "Bookmark(String url)생성자 호출 this.url =="+this.url);
        //this.title = title;
        this.url = url;

    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
    @Override
    public boolean equals(Object object){
        return object instanceof Bookmark && url.equals(((Bookmark)object).getUrl());

    }
}

