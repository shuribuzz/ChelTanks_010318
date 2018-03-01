package ru.alexandrpokh.cheltanks.homePackage;


public class Newscard {
    private String newstitle, date, descript, link, photo;

    public Newscard(String newstitle, String date, String descript, String link, String photo) {
        this.newstitle = newstitle;
        this.date = date;
        this.descript = descript;
        this.link = link;
        this.photo = photo;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
