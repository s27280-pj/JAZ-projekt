package pl.bookstore.bookshop.config;

public class AuthorityList {
    private static final String ADMINISTRATOR = "ADMINISTRATOR";
    private static final String USER = "USER";
    protected static String[] anyAuthority = {ADMINISTRATOR, USER};
    protected static String adminAuthority = ADMINISTRATOR;
}
