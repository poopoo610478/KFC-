package com.ispan.chufa.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "coupon")
public class CouponBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId; // couponId 流水號 封面不需要輸入 但會在新增後取值

    @Column(name = "coupon_code")
    private String couponCode; // 優惠券代碼，要求唯一且不能為空

    @Column(name = "TimesOfUse")
    private Integer TimesOfUse; // 庫存 為數字
    
    @Column(name = "remaining")
    private Integer remaining; // 庫存 為數字

    @Column(name = "price")
    private Integer price; // 庫存 為數字

    @Column(name = "Title")
    private String title; // 標題為 字串

    @Column(name = "Content")
    private String content; // 內容 為字串

    @Column(name = "State")
    private Boolean state; // 狀態封面不需輸入 按下新增或修改時記錄當下時間。 在startTime和endTime之間時為'有效'，其餘為無效

    @Column(name = "Picture")
    private String picture;

    @Column(name = "eggTart")
    private Integer eggTart;

    @Column(name = "friedChicken")
    private Integer friedChicken;
    
    @Column(name = "hamburger")
    private Integer hamburger;

    @Column(name = "chickenNuggets")
    private Integer chickenNuggets;

    @Column(name = "drinks")
    private Integer drinks;

    @Column(name = "fries")
    private Integer fries;

    @Column(name = "QQBall")
    private Integer QQBall;

    @Column(name = "chikenRice")
    private Integer chikenRice;

    @Column(name = "herbChiken")
    private Integer herbChiken;

    @Column(name = "MexicoChiken")
    private Integer MexicoChiken;

    @Column(name = "FishDonut")
    private Integer FishDonut;

    @Column(name = "ShrimpNugget")
    private Integer ShrimpNugget;

    @Column(name = "Breakfast")
    private Integer breakfast;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "StartTime")
    private LocalDateTime startTime; // 開始時間 為localdatetime

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "EndTime")
    private LocalDateTime endTime; // 結束時間 為localdatetime

    @Column(name = "TaipeiFuhSingNan")//台北復興南
    private Boolean TaipeiFuhSingNan;

    @Column(name = "TaipeiKuangFu")//台北光復
    private Boolean TaipeiKuangFu;

    @Column(name = "SinTiamAnKang")//新店安康
    private Boolean SinTiamAnKang;

    @Column(name = "SinTiamBaLi")//新店八里
    private Boolean SinTiamBaLi;

    @Column(name = "YilanCarrefour")//宜蘭家樂福
    private Boolean YilanCarrefour;

    @Column(name = "TaichungFuhSing")//台中光復
    private Boolean TaichungFuhSing;

    @Column(name = "PingTungDongGang")//屏東東港
    private Boolean PingTungDongGang;

    @Column(name = "TaoyuanHighRail")//桃園高鐵
    private Boolean TaoyuanHighRail;

    @Column(name = "YilanJiaoxi")//宜蘭礁溪
    private Boolean YilanJiaoxi;

    @ManyToMany(mappedBy = "couponBean")
    private List<MyCouponBean> myCoupons;

    @ManyToOne
    @JoinColumn(name = "placeId")
    private PlaceBean place;

    // Getters and Setters
    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getTimesOfUse() {
        return TimesOfUse;
    }

    public void setTimesOfUse(Integer TimesOfUse) {
        this.TimesOfUse = TimesOfUse;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getEggTart() {
        return eggTart;
    }

    public void setEggTart(Integer eggTart) {
        this.eggTart = eggTart;
    }

    public Integer getFriedChicken() {
        return friedChicken;
    }

    public void setFriedChicken(Integer friedChicken) {
        this.friedChicken = friedChicken;
    }
    
    public Integer getHamburger() {
        return hamburger;
    }

    public void setHamburger(Integer hamburger) {
        this.hamburger = hamburger;
    }

    public Integer getChickenNuggets() {
        return chickenNuggets;
    }

    public void setChickenNuggets(Integer chickenNuggets) {
        this.chickenNuggets = chickenNuggets;
    }

    public Integer getDrinks() {
        return drinks;
    }

    public void setDrinks(Integer drinks) {
        this.drinks = drinks;
    }

    public Integer getFries() {
        return fries;
    }

    public void setFries(Integer fries) {
        this.fries = fries;
    }

    public Integer getQQBall() {
        return QQBall;
    }

    public void setQQBall(Integer QQBall) {
        this.QQBall = QQBall;
    }

    public Integer getChikenRice() {
        return chikenRice;
    }

    public void setChikenRice(Integer chikenRice) {
        this.chikenRice = chikenRice;
    }

    public Integer getHerbChiken() {
        return herbChiken;
    }

    public void setHerbChiken(Integer herbChiken) {
        this.herbChiken = herbChiken;
    }

    public Integer getMexicoChiken() {
        return MexicoChiken;
    }

    public void setMexicoChiken(Integer MexicoChiken) {
        this.MexicoChiken = MexicoChiken;
    }

    
    public Integer getFishDonut() {
        return FishDonut;
    }
    
    public void setFishDonut(Integer FishDonut) {
        this.FishDonut = FishDonut;
    }
    
    public Integer getShrimpNugget() {
        return ShrimpNugget;
    }

    public void setShrimpNugget(Integer ShrimpNugget) {
        this.ShrimpNugget = ShrimpNugget;
    }

    public Integer getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getTaipeiFuhSingNan() {
        return TaipeiFuhSingNan;
    }

    public void setTaipeiFuhSingNan(Boolean TaipeiFuhSingNan) {
        this.TaipeiFuhSingNan = TaipeiFuhSingNan;
    }

    public Boolean getTaipeiKuangFu() {
        return TaipeiKuangFu;
    }

    public void setTaipeiKuangFu(Boolean TaipeiKuangFu) {
        this.TaipeiKuangFu = TaipeiKuangFu;
    }

    public Boolean getSinTiamAnKang() {
        return SinTiamAnKang;
    }

    public void setSinTiamAnKang(Boolean SinTiamAnKang) {
        this.SinTiamAnKang = SinTiamAnKang;
    }

    public Boolean getSinTiamBaLi() {
        return SinTiamBaLi;
    }

    public void setSinTiamBaLi(Boolean SinTiamBaLi) {
        this.SinTiamBaLi = SinTiamBaLi;
    }

    public Boolean getYilanCarrefour() {
        return YilanCarrefour;
    }

    public void setYilanCarrefour(Boolean YilanCarrefour) {
        this.YilanCarrefour = YilanCarrefour;
    }

    public Boolean getTaichungFuhSing() {
        return TaichungFuhSing;
    }

    public void setTaichungFuhSing(Boolean TaichungFuhSing) {
        this.TaichungFuhSing = TaichungFuhSing;
    }

    public Boolean getPingTungDongGang() {
        return PingTungDongGang;
    }

    public void setPingTungDongGang(Boolean PingTungDongGang) {
        this.PingTungDongGang = PingTungDongGang;
    }

    public Boolean getTaoyuanHighRail() {
        return TaoyuanHighRail;
    }

    public void setTaoyuanHighRail(Boolean TaoyuanHighRail) {
        this.TaoyuanHighRail = TaoyuanHighRail;
    }

    public Boolean getYilanJiaoxi() {
        return YilanJiaoxi;
    }

    public void setYilanJiaoxi(Boolean YilanJiaoxi) {
        this.YilanJiaoxi = YilanJiaoxi;
    }

    public List<MyCouponBean> getMyCoupons() {
        return myCoupons;
    }

    public void setMyCoupons(List<MyCouponBean> myCoupons) {
        this.myCoupons = myCoupons;
    }

    public PlaceBean getPlace() {
        return place;
    }

    public void setPlace(PlaceBean place) {
        this.place = place;
    }
}
