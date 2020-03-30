
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
 *         &lt;element name="HasSmsEnabledResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "hasSmsEnabledResult"
})
@XmlRootElement(name = "HasSmsEnabledResponse")
public class HasSmsEnabledResponse {

    @XmlElement(name = "HasSmsEnabledResult")
    protected boolean hasSmsEnabledResult;

    /**
     * Gets the value of the hasSmsEnabledResult property.
     * 
     */
    public boolean isHasSmsEnabledResult() {
        return hasSmsEnabledResult;
    }

    /**
     * Sets the value of the hasSmsEnabledResult property.
     * 
     */
    public void setHasSmsEnabledResult(boolean value) {
        this.hasSmsEnabledResult = value;
    }

}
