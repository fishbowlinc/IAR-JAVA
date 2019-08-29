
package com.fb.in.app.reporting.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ssoUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ssoUsername",
    "entUsername",
    "token",
    "entPassword"
})
@XmlRootElement(name = "LinkSSOToEnterprise")
public class LinkSSOToEnterprise {

    protected String ssoUsername;
    protected String entUsername;
    protected String token;
    protected String entPassword;

    /**
     * Gets the value of the ssoUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsoUsername() {
        return ssoUsername;
    }

    /**
     * Sets the value of the ssoUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsoUsername(String value) {
        this.ssoUsername = value;
    }

    /**
     * Gets the value of the entUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntUsername() {
        return entUsername;
    }

    /**
     * Sets the value of the entUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntUsername(String value) {
        this.entUsername = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the entPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntPassword() {
        return entPassword;
    }

    /**
     * Sets the value of the entPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntPassword(String value) {
        this.entPassword = value;
    }

}
