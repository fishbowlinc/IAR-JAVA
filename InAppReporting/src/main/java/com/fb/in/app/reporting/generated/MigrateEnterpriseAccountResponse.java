
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
 *         &lt;element name="MigrateEnterpriseAccountResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "migrateEnterpriseAccountResult"
})
@XmlRootElement(name = "MigrateEnterpriseAccountResponse")
public class MigrateEnterpriseAccountResponse {

    @XmlElement(name = "MigrateEnterpriseAccountResult")
    protected String migrateEnterpriseAccountResult;

    /**
     * Gets the value of the migrateEnterpriseAccountResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMigrateEnterpriseAccountResult() {
        return migrateEnterpriseAccountResult;
    }

    /**
     * Sets the value of the migrateEnterpriseAccountResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMigrateEnterpriseAccountResult(String value) {
        this.migrateEnterpriseAccountResult = value;
    }

}
