package com.rg.testbot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "daily_domains")
public class DailyDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_domains_id_seq")
    @SequenceGenerator(name = "daily_domains_id_seq", sequenceName = "daily_domains_id_seq", allocationSize = 1)
    private long id;

    private String domainname;
    private short hotness;
    private int price;
    private short xValue;
    private int yandexTic;
    private int links;
    private int visitors;
    private String registrar;
    private short old;
    private Date deleteDate;

    private boolean rkn;
    private boolean judicial;
    private boolean block;

    public DailyDomain() {
    }

    public DailyDomain(String domainname, short hotness, int price, short xValue, int yandexTic,
                       int links, int visitors, String registrar, short old, Date deleteDate,
                       boolean rkn, boolean judicial, boolean block) {
        this.domainname = domainname;
        this.hotness = hotness;
        this.price = price;
        this.xValue = xValue;
        this.yandexTic = yandexTic;
        this.links = links;
        this.visitors = visitors;
        this.registrar = registrar;
        this.old = old;
        this.deleteDate = deleteDate;
        this.rkn = rkn;
        this.judicial = judicial;
        this.block = block;
    }
}
