/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.model;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class Client implements Serializable {

    private Integer clientId;
    private String clientName;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Client{" + "clientId=" + clientId + ", clientName=" + clientName + '}';
    }

}
