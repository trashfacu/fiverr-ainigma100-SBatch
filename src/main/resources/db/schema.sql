CREATE TABLE ACCOUNT_ERM
(
    interestType     VARCHAR(255) PRIMARY KEY,
    interestProperty VARCHAR(255),
    compoundType     VARCHAR(255),
    interestRateType VARCHAR(255),
    customerErm      VARCHAR(255),
    executionDate    TIMESTAMP,
    FOREIGN KEY (customerErm) REFERENCES CUSTOMER_ERM (accountId)
);

CREATE TABLE CUSTOMER_ERM
(
    accountId                 VARCHAR(255) PRIMARY KEY,
    customerId                VARCHAR(255),
    arrangementId             VARCHAR(255),
    productLineName           VARCHAR(255),
    productGroupId            VARCHAR(255),
    productGroupName          VARCHAR(255),
    productId                 VARCHAR(255),
    productDescription        VARCHAR(255),
    shortTitle                VARCHAR(255),
    categoryId                VARCHAR(255),
    companyCode               VARCHAR(255),
    currencyId                VARCHAR(255),
    sortCode                  VARCHAR(255),
    accountIBAN               VARCHAR(255),
    workingBalance            DOUBLE,
    openingDate               DATE,
    companyName               VARCHAR(255),
    preferredProduct          VARCHAR(255),
    preferredProductPosition  VARCHAR(255),
    preferredProductLabel     VARCHAR(255),
    permission                VARCHAR(255),
    onlineActualBalance       DOUBLE,
    availableBalance          DOUBLE,
    availableBalanceWithLimit DOUBLE,
    outstandingAmount         DOUBLE,
    paidOutAmount             DOUBLE,
    arrangementStatus         VARCHAR(255),
    totalPrincipal            DOUBLE,
    customerReference         VARCHAR(255),
    statement                 VARCHAR(255),
    portfolioId               VARCHAR(255),
    connectionId              VARCHAR(255),
    balancesLastUpdated       TIMESTAMP WITH TIME ZONE,
    transactionsLastUpdated   TIMESTAMP WITH TIME ZONE,
    bankLogo                  VARCHAR(255),
    extSourceProvider         VARCHAR(255),
    connectionStatus          VARCHAR(255),
    connectionSubStatus       VARCHAR(255),
    nextRefreshAvailableAt    TIMESTAMP WITH TIME ZONE,
    acctSwiftRef              VARCHAR(255),
    extAccountNumber          VARCHAR(255),
    countryCode               VARCHAR(255),
    executionDate             TIMESTAMP
);

CREATE TABLE INVALID_RECORD
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    recordType       VARCHAR(255),
    details          TEXT,
    exceptionType    VARCHAR(255),
    exceptionMessage TEXT,
    stepName         VARCHAR(255),
    timeStamp        TIMESTAMP
);

CREATE TABLE API_EXECUTION_DETAILS
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    requestUrl             VARCHAR(255),
    queryParameters        TEXT,
    pathVariables          TEXT,
    requestHeaders         TEXT,
    responseHttpStatusCode VARCHAR(255),
    exceptionMessage       TEXT,
    startExecDate          TIMESTAMP,
    endExecDate            TIMESTAMP,
    durationExec           BIGINT
);

CREATE TABLE ERM_OUT_4
(
    accountErmOutId BIGINT AUTO_INCREMENT PRIMARY KEY,
    interestType    VARCHAR(255),
    customerErm     VARCHAR(255),
    executionDate   TIMESTAMP,
    FOREIGN KEY (customerErm) REFERENCES CUSTOMER_ERM (accountId)
);

CREATE TABLE ERM_OUT_5
(
    customerErmOutId   BIGINT AUTO_INCREMENT PRIMARY KEY,
    customerId         VARCHAR(255),
    arrangementId      VARCHAR(255),
    productLineName    VARCHAR(255),
    productGroupId     VARCHAR(255),
    productGroupName   VARCHAR(255),
    productDescription VARCHAR(255),
    shortTitle         VARCHAR(255),
    categoryId         VARCHAR(255),
    companyCode        VARCHAR(255),
    sortCode           VARCHAR(255),
    executionDate      TIMESTAMP
);