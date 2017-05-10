package app.com.warattil.model;


public class Surah {

    private int id;
    private String firstReciter;
    private String secondReciter;
    private String titleEnglish;
    private String titleArabic;

    public Surah(String secondReciter, int id, String firstReciter, String titleEnglish, String titleArabic) {
        this.id = id;
        this.secondReciter = secondReciter;
        this.firstReciter = firstReciter;
        this.titleEnglish = titleEnglish;
        this.titleArabic = titleArabic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecondReciter() {
        return secondReciter;
    }

    public void setSecondReciter(String secondReciter) {
        this.secondReciter = secondReciter;
    }

    public String getFirstReciter() {
        return firstReciter;
    }

    public void setFirstReciter(String firstReciter) {
        this.firstReciter = firstReciter;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleArabic() {
        return titleArabic;
    }

    public void setTitleArabic(String titleArabic) {
        this.titleArabic = titleArabic;
    }
}