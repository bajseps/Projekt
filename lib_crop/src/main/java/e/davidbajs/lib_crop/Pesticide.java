package e.davidbajs.lib_crop;

import java.math.BigDecimal;
import java.util.Date;

public class Pesticide {
    private String idApp;
    private String name;
    private String type;
    private Date dateOfSpray;
    private BigDecimal price;

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateOfSpray() {
        return dateOfSpray;
    }

    public void setDateOfSpray(Date dateOfSpray) {
        this.dateOfSpray = dateOfSpray;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
