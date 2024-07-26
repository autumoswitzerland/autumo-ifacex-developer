#!/bin/sh

###############################################################################
#
#  autumo ifaceX developer project packager.
#  Version: 2.0
#
#  Notes:
#   -
#
#------------------------------------------------------------------------------
#
#  2022 autumo GmbH
#  Date: 07.12.2023
#
###############################################################################




# VARS
IFACEX_VERSION=2.0.0




# ------------------------------------------------
# -------- Usage
# ------------------------------------------------
if [ "$1" = "help" -o "$#" -lt 1 ]
then
	echo " "
	echo "make clear"
	echo "make create"
	echo " "
	echo " "
	exit 1
fi


mkdir -p product


# ------------------------------------------------
# -------- DELETE PRODUCT
# ------------------------------------------------
if [ "$1" = "clear" ]
then
	cd product

	# remove working directory
	if [ -d "autumo-ifaceX-Developer-$IFACEX_VERSION" ]
	then
		rm -Rf autumo-ifaceX-Developer-$IFACEX_VERSION
	fi
	
	# remove package
	if [ -f "autumo-ifaceX-Developer-$IFACEX_VERSION.zip" ]
	then
    	rm autumo-ifaceX-Developer-$IFACEX_VERSION.zip
	fi	
	
	cd ..
	
	exit 1
fi



# ------------------------------------------------
# -------- CREATE PRODUCT
# ------------------------------------------------
if [ "$1" = "create" -a "$#" -gt 0 ]
then



# -----------------------------
# ---- Cleanup & Prepare
# -----------------------------

	echo "-> Cleanup & prepare..."
	
	# delete old product package
	if [ -d "product/autumo-ifaceX-Developer-$IFACEX_VERSION" ]
	then
		rm -Rf product/autumo-ifaceX-Developer-$IFACEX_VERSION
	fi	

	# go to product
	cd product
	
	# make working directory
	mkdir autumo-ifaceX-Developer-$IFACEX_VERSION



# -----------------------------
# ---- Go to ifaceX package
# -----------------------------

	# go to ifaceX package folder
	cd autumo-ifaceX-Developer-$IFACEX_VERSION



# -----------------------------
# ---- Copying
# -----------------------------
		

	echo "-> Copying..."
	
	mkdir -p lib
	cp ../../lib/*.jar lib/

	mkdir src
	cp -R ../../src .

	mkdir .settings
	cp -R ../../.settings .
	
	cp ../../pom.xml .
	cp ../../.classpath .
	cp ../../.project .

	cp ../../maven-install.sh .
	cp ../../maven-install.bat .

	cp ../../README.md .
	cp ../../LICENSE.md .

	

# -----------------------------
# ---- Create Product
# -----------------------------

	echo "-> Create PRODUCT..."

	# LEAVE PACKAGE FOLDER
	cd ..

	# create archive
	zip -r "autumo-ifaceX-Developer-${IFACEX_VERSION}.zip" autumo-ifaceX-Developer-${IFACEX_VERSION} \
		-x "*/.DS_Store" \
		-x "*/__MACOSX"

	# delete build directory
	rm -Rf autumo-ifaceX-Developer-${IFACEX_VERSION}
	

	
# -----------------------------
# ---- END
# -----------------------------
	
	# leave product folder
	cd ..

	
else
	echo "Nope! -> make create|clear"
	echo " "
fi



