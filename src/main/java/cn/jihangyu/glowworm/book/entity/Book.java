package cn.jihangyu.glowworm.book.entity;

public class Book {
    private Integer bId;

    private String bName;

    private String bAuthor;

    private String bPublisher;

    private String bPrice;

    private String bIsbn;

    private String bBarcode;

    private Integer bOwnerId;

    private Integer bUserId;

    private String bStatus;

    public Integer getbId() {
        return bId;
    }

    public void setbId(Integer bId) {
        this.bId = bId;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName == null ? null : bName.trim();
    }

    public String getbAuthor() {
        return bAuthor;
    }

    public void setbAuthor(String bAuthor) {
        this.bAuthor = bAuthor == null ? null : bAuthor.trim();
    }

    public String getbPublisher() {
        return bPublisher;
    }

    public void setbPublisher(String bPublisher) {
        this.bPublisher = bPublisher == null ? null : bPublisher.trim();
    }

    public String getbPrice() {
        return bPrice;
    }

    public void setbPrice(String bPrice) {
        this.bPrice = bPrice == null ? null : bPrice.trim();
    }

    public String getbIsbn() {
        return bIsbn;
    }

    public void setbIsbn(String bIsbn) {
        this.bIsbn = bIsbn == null ? null : bIsbn.trim();
    }

    public String getbBarcode() {
        return bBarcode;
    }

    public void setbBarcode(String bBarcode) {
        this.bBarcode = bBarcode == null ? null : bBarcode.trim();
    }

    public Integer getbOwnerId() {
        return bOwnerId;
    }

    public void setbOwnerId(Integer bOwnerId) {
        this.bOwnerId = bOwnerId;
    }

    public Integer getbUserId() {
        return bUserId;
    }

    public void setbUserId(Integer bUserId) {
        this.bUserId = bUserId;
    }

    public String getbStatus() {
        return bStatus;
    }

    public void setbStatus(String bStatus) {
        this.bStatus = bStatus == null ? null : bStatus.trim();
    }
}