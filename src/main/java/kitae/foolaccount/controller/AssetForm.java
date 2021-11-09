package kitae.foolaccount.controller;

public class AssetForm {

    private Long sequence;
    private String id;
    private String kind;
    private String corporation;
    private Long count;
    private Long averagePrice;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Long averagePrice) {
        this.averagePrice = averagePrice;
    }
}
