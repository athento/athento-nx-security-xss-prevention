# athento-nx-security-xss-prevention

## For versions
6.0

## Synopsis

This plugin prevents XSS store attack vector when creating/editting a user and when this data is rendered in the suggestion-box.

## Steps to XSS attack

1 Create a user using REST API (also using UI form)
```{r, engine='bash', count_lines}
curl -X POST -H "Content-Type: application/json" -u Administrator:Administrator -d '{ "entity-type": "user", "id":"xssuser", "properties":{"username":"xssuser", "email":"xss@athento.com", "lastName":"XSS attack!", "firstName":"<script>alert(\"You have been hacked!\");</script>", "password":"xsspasswd" } }' http://localhost:8080/nuxeo/api/v1/user
```

2 This will result in this situation
![Alt text](http://oi64.tinypic.com/2cwkhma.jpg "Dangerous user created")


3 When you try to search the user using the suggestion box (on the top-right corner of the page) you'll get the following message:
![Alt text](http://oi68.tinypic.com/ivhg6t.jpg "XSS attack success")


## Installation

You just have to compile the pom.xml using Maven and deploy the plugin in 
```{r, engine='bash', count_lines}
cd nuxeo-field-validator-plugin
mvn clean install
cp target/fieldValidator-*.jar $NUXEO_HOME/nxserver/plugins
```
And then, restart your nuxeo server and enjoy.
