
package com.fb.in.app.reporting.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="HasSocialEnabledResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "hasSocialEnabledResult"
})
@XmlRootElement(name = "HasSocialEnabledResponse")
public class HasSocialEnabledResponse {

    @XmlElement(name = "HasSocialEnabledResult")
    protected boolean hasSocialEnabledResult;

    /**
     * Gets the value of the hasSocialEnabledResult property.
     * 
     */
    public boolean isHasSocialEnabledResult() {
        return hasSocialEnabledResult;
    }

    /**
     * Sets the value of the hasSocialEnabledResult property.
     * 
     */
    public void setHasSocialEnabledResult(boolean value) {
        this.hasSocialEnabledResult = value;
    }

}
