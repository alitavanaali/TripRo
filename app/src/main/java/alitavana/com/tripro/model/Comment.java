package alitavana.com.tripro.model;

/**
 * Created by Ali Tavana on 30/04/2017.
 */

public class Comment {
    String commentTitle, commentText, commentRate, commentDate;

    public Comment() {
    }

    public Comment(String commentTitle, String commentText, String commentRate, String commentDate) {
        this.commentTitle = commentTitle;
        this.commentText = commentText;
        this.commentRate = commentRate;
        this.commentDate = commentDate;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentRate() {
        return commentRate;
    }

    public void setCommentRate(String commentRate) {
        this.commentRate = commentRate;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}
