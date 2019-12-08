/* Copyright 2019 freecodeformat.com */
package org.acme.shakesperean_pokedex.dto.fun_translations;

/* Time: 2019-12-08 19:44:20 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class Translation {

    private Success success;
    private Contents contents;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

}