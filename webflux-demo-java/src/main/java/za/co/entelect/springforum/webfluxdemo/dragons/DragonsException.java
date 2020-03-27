package za.co.entelect.springforum.webfluxdemo.dragons;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public class DragonsException extends RuntimeException {
        public DragonsException(String s) {
            super(s);
        }
    }

