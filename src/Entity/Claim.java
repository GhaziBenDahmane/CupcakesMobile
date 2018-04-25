package Entity;

import java.util.Date;

/**
 *
 * @author ding
 */
public class Claim {

    private int id;
    private User client;
    private User answeredBy;
    private String description;
    private String answer;
    private String type;
    private Date postedOn;
    private boolean answered;

    public Claim() {
    }

    public Claim(User client, String description, Date postedOn, String type) {
        this.client = client;
        this.description = description;
        this.postedOn = new Date();
        this.type = type;
    }

    public Claim(int id, User client, User answeredBy, String description, String answer, Date postedOn, boolean answered, String type) {
        this.id = id;
        this.client = client;
        this.answeredBy = answeredBy;
        this.description = description;
        this.answer = answer;
        this.postedOn = postedOn;
        this.answered = answered;
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getAnsweredBy() {
        return answeredBy;
    }

    public void setAnsweredBy(User answeredBy) {
        this.answeredBy = answeredBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    @Override
    public String toString() {
        return "Claim{" + "id=" + id + ", client=" + client + ", answeredBy=" + answeredBy + ", description=" + description + ", answer=" + answer + ", postedOn=" + postedOn + ", answered=" + answered + '}';
    }

}
