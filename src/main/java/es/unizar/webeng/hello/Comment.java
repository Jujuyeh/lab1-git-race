package es.unizar.webeng.hello;

import java.util.Date;

public class Comment {
    
    /** Content of the comment */
    private String comment;

    /** Author of the comment */
    private String name;

    /** Date of the comment */
    private Date date;
    
    /**
     * Construcor
     * @param comment Content of the comment
     * @param name Name of the comment
     * @param date Date of the comment
     */
    public Comment(String comment, String name, Date date) {
        this.comment = comment;
        this.name = name;
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}