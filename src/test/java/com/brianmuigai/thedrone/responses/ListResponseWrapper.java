package com.brianmuigai.thedrone.responses;

public class ListResponseWrapper {
    public ListResponse _embedded;

    public ListResponse get_embedded() {
        return _embedded;
    }

    public void set_embedded(ListResponse _embedded) {
        this._embedded = _embedded;
    }
}
