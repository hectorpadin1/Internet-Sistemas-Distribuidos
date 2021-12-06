-- ----------------------------------------------------------------------------
-- RunFic Model
-------------------------------------------------------------------------------

DROP TABLE Inscription;
DROP TABLE Race;


-- --------------------------- Race -------------------------------------------

CREATE TABLE Race (
    raceId BIGINT NOT NULL AUTO_INCREMENT,
    city VARCHAR(255) COLLATE latin1_bin NOT NULL,
    description VARCHAR(1024) COLLATE latin1_bin,
    raceDate DATETIME NOT NULL,
    price FLOAT NOT NULL,
    maxParticipants SMALLINT NOT NULL,
    creationDate DATETIME NOT NULL,
    inscriptions SMALLINT DEFAULT 0,

    CONSTRAINT RacePK PRIMARY KEY(raceId),
    CONSTRAINT validRacePrice CHECK (price >= 0),
    CONSTRAINT validParticipants CHECK (maxParticipants >= 0) ) ENGINE = InnoDB;


-- -------------------------- Inscription -------------------------------------

CREATE TABLE Inscription(
    inscriptionId BIGINT NOT NULL AUTO_INCREMENT,
    userEmail VARCHAR(20) COLLATE latin1_bin NOT NULL,
    raceId BIGINT NOT NULL,
    creditCardNumber VARCHAR(16) NOT NULL,
    inscriptionDate DATETIME NOT NULL,
    dorsal SMALLINT NOT NULL,
    picked_up BIT DEFAULT 0,

    CONSTRAINT InscriptionPK PRIMARY KEY(inscriptionId),
    FOREIGN KEY(raceId) REFERENCES Race(raceId) ) ENGINE = InnoDB;