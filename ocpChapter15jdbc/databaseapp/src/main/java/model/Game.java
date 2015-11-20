package model;

/**
 * 
 * @author xiaochenzhang
 *
 */
public class Game {

    private String name;

    private String price;

    private String review;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public String getReview() {
        return review;
    }

    public void setReview(final String review) {
        this.review = review;
    }

}
