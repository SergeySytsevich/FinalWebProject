package com.epam.webproject.entity;

public class Hotel extends Entity{
     private Integer id;
     private String name;
     private String address;
     private String city;
     private String Country;
     private String mainPhoneNumber;
     private String faxNumber;
     private String receptionEmail;
     private String webSite;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the Country
     */
    public String getCountry() {
        return Country;
    }

    /**
     * @param Country the Country to set
     */
    public void setCountry(String Country) {
        this.Country = Country;
    }

    /**
     * @return the mainPhoneNumber
     */
    public String getMainPhoneNumber() {
        return mainPhoneNumber;
    }

    /**
     * @param mainPhoneNumber the mainPhoneNumber to set
     */
    public void setMainPhoneNumber(String mainPhoneNumber) {
        this.mainPhoneNumber = mainPhoneNumber;
    }

    /**
     * @return the faxNumber
     */
    public String getFaxNumber() {
        return faxNumber;
    }

    /**
     * @param faxNumber the faxNumber to set
     */
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    /**
     * @return the receptionEmail
     */
    public String getReceptionEmail() {
        return receptionEmail;
    }

    /**
     * @param receptionEmail the receptionEmail to set
     */
    public void setReceptionEmail(String receptionEmail) {
        this.receptionEmail = receptionEmail;
    }

    /**
     * @return the webSite
     */
    public String getWebSite() {
        return webSite;
    }

    /**
     * @param webSite the webSite to set
     */
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    // FIXME IN ALL Entities : equals, hashcode & tostring 
}
