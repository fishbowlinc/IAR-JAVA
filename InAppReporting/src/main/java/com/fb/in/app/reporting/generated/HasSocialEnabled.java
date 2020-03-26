
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
 *         &lt;element name="siteId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ssoUserId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="administratorId" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "siteId",
    "ssoUserId",
    "username",
    "administratorId"
})
@XmlRootElement(name = "HasSocialEnabled")
public class HasSocialEnabled {

    protected long siteId;
    protected int ssoUserId;
    protected String username;
    protected int administratorId;

    /**
     * Gets the value of the siteId property.
     * 
     */
    public long getSiteId() {
        return siteId;
    }

    /**
     * Sets the value of the siteId property.
     * 
     */
    public void setSiteId(long value) {
        this.siteId = value;
    }

    /**
     * Gets the value of the ssoUserId property.
     * 
     */
    public int getSsoUserId() {
        return ssoUserId;
    }

    /**
     * Sets the value of the ssoUserId property.
     * 
     */
    public void setSsoUserId(int value) {
        this.ssoUserId = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the administratorId property.
     * 
     */
    public int getAdministratorId() {
        return administratorId;
    }

    /**
     * Sets the value of the administratorId property.
     * 
     */
    public void setAdministratorId(int value) {
        this.administratorId = value;
    }

}
