package chenjiuxu.okhttppack;

/**
 * Created by 15705 on 2017/9/14.
 */

public class SitesBean {


    private String guo;
    private String shen;

    public String getGuo() {
        return guo;
    }

    public void setGuo(String guo) {
        this.guo = guo;
    }

    public String getShen() {
        return shen;
    }

    public void setShen(String shen) {
        this.shen = shen;
    }

    @Override
    public String toString() {
        return "SitesBean{" +
                "guo='" + guo + '\'' +
                ", shen='" + shen + '\'' +
                '}';
    }

}
