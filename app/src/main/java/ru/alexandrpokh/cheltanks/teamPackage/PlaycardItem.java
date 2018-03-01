package ru.alexandrpokh.cheltanks.teamPackage;

public class PlaycardItem {
    private String fio, info, photo, link, num, pos;

    public PlaycardItem(String fio, String info, String link, String photo, String num, String pos) {
        this.fio = fio;
        this.info = info;
        this.link = link;
        this.photo = photo;
        this.num = num;
        this.pos = pos;
    }

    public String getFio() {
        return fio;
    }

    public String getInfo() {
        return info;
    }

    public String getLink() {
        return link;
    }

    public String getPhoto() {
        return photo;
    }

    public String getNum() {
        return num;
    }

    public String getPos() {
        return pos;
    }

}