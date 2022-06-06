package hsrt.inf2p.backendapi.model;

public class StatusTransferObject {
    private String status;
    public StatusTransferObject(){
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
