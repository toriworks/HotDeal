package tori.app.msholmes.utils.domain;

public class Feed {

    /** 국내, 국외, Q&A 구분 */
    private String feedCategory;
    /** 스크랩 여부 */
    private int isScap;
    /** 좋아요 여부 */
    private int isLike;
    /** 블라인드 처리 여부 */
    private int isBlind;
    /** 제목 */
    private String feedTitle;
    /** 내용 */
    private String feedContent;
    /** 피드등록 시간 */
    private String regdate;
    /** 첨부이미지 경로 */
    private String attachedFilePath;

    /**
     * Gets attached file path.
     *
     * @return the attached file path
     */
    public String getAttachedFilePath() {
        return attachedFilePath;
    }

    /**
     * Sets attached file path.
     *
     * @param attachedFilePath the attached file path
     */
    public void setAttachedFilePath(String attachedFilePath) {
        this.attachedFilePath = attachedFilePath;
    }

    /**
     * Gets feed category.
     *
     * @return the feed category
     */
    public String getFeedCategory() {
        return feedCategory;
    }

    /**
     * Sets feed category.
     *
     * @param feedCategory the feed category
     */
    public void setFeedCategory(String feedCategory) {
        this.feedCategory = feedCategory;
    }

    /**
     * Gets feed content.
     *
     * @return the feed content
     */
    public String getFeedContent() {
        return feedContent;
    }

    /**
     * Sets feed content.
     *
     * @param feedContent the feed content
     */
    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    /**
     * Gets feed title.
     *
     * @return the feed title
     */
    public String getFeedTitle() {
        return feedTitle;
    }

    /**
     * Sets feed title.
     *
     * @param feedTitle the feed title
     */
    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    /**
     * Gets blind.
     *
     * @return the blind
     */
    public int getBlind() {
        return isBlind;
    }

    /**
     * Sets blind.
     *
     * @param blind the blind
     */
    public void setBlind(int blind) {
        isBlind = blind;
    }

    /**
     * Gets like.
     *
     * @return the like
     */
    public int getLike() {
        return isLike;
    }

    /**
     * Sets like.
     *
     * @param like the like
     */
    public void setLike(int like) {
        isLike = like;
    }

    /**
     * Gets scap.
     *
     * @return the scap
     */
    public int getScap() {
        return isScap;
    }

    /**
     * Sets scap.
     *
     * @param scap the scap
     */
    public void setScap(int scap) {
        isScap = scap;
    }

    /**
     * Gets regdate.
     *
     * @return the regdate
     */
    public String getRegdate() {
        return regdate;
    }

    /**
     * Sets regdate.
     *
     * @param regdate the regdate
     */
    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "attachedFilePath='" + attachedFilePath + '\'' +
                ", feedCategory='" + feedCategory + '\'' +
                ", isScap=" + isScap +
                ", isLike=" + isLike +
                ", isBlind=" + isBlind +
                ", feedTitle='" + feedTitle + '\'' +
                ", feedContent='" + feedContent + '\'' +
                ", regdate='" + regdate + '\'' +
                '}';
    }
}
