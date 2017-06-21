/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */

package com.amcoder.pranku.address;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "AddressFields")
public class AddressFields extends Model implements Serializable, Cloneable {

    private static final long serialVersionUID = 8533528348900570462L;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "titleCode")
    private String titleCode;

    @Column(name = "countryIsocode")
    private String countryIsocode;

    @Column(name = "email")
    private String email;

    @Column(name = "line1")
    private String line1;

    @Column(name = "line2")
    private String line2;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name = "town")
    private String town;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "regionIsoCode")
    private String regionIsoCode;

    @Column(name = "regionName")
    private String regionName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    public String getCountryIsocode() {
        return countryIsocode;
    }

    public void setCountryIsocode(String countryIsocode) {
        this.countryIsocode = countryIsocode;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegionIsoCode() {
        return regionIsoCode;
    }

    public void setRegionIsoCode(String regionIsoCode) {
        this.regionIsoCode = regionIsoCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public AddressFields() {
        super();
    }

    @Override
    public AddressFields clone() {
        AddressFields newFields = new AddressFields();
        newFields.firstName = firstName;
        newFields.lastName = lastName;
        newFields.titleCode = titleCode;
        newFields.countryIsocode = countryIsocode;
        newFields.email = email;
        newFields.line1 = line1;
        newFields.line2 = line2;
        newFields.postalCode = postalCode;
        newFields.town = town;
        newFields.phoneNumber = phoneNumber;
        newFields.regionIsoCode = regionIsoCode;
        newFields.regionName = regionName;
        return newFields;
    }

    public String getFormattedAddress() {
        return titleCode + firstName + lastName + line1 + postalCode + town + phoneNumber;
    }
}
