
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
 *         &lt;element name="GetActiveEnterpriseAccountResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getActiveEnterpriseAccountResult"
})
@XmlRootElement(name = "GetActiveEnterpriseAccountResponse")
public class GetActiveEnterpriseAccountResponse {

    @XmlElement(name = "GetActiveEnterpriseAccountResult")
    protected String getActiveEnterpriseAccountResult;

    /**
     * Gets the value of the getActiveEnterpriseAccountResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetActiveEnterpriseAccountResult() {
        return getActiveEnterpriseAccountResult;
    }

    /**
     * Sets the value of the getActiveEnterpriseAccountResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetActiveEnterpriseAccountResult(String value) {
        this.getActiveEnterpriseAccountResult = value;
    }

}
