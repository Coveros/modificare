#!/usr/bin/perl

## This file is part of SCATTR
##
## SCATTR is free software:  you can redistrubite it and/or modify it under the 
## terms of the GNU General Public License as published by the Free Software 
## Foundation, either version 3 of the License, or any later version.
##
## SCATTR is distributed in the hope that it will be useful, but WITHOUT ANY 
## WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
## FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
## details.
##
## You should have received a copy of the GNU General Public License along with 
## Foobar.  If not, see <http://www.gnu.org/licenses/>.
##
## Copyright 2007

# This Perl script will read the data contained
# in src.txt and test.txt in order to translate
# the data into the proper format required to 
# generate the histogram plots

$dir = "data";
$src_file = "$dir/src.txt";
$test_file = "$dir/test.txt";

# Open the files src.txt and test.txt for reading
open SRC, $src_file or die "$src_file not found";
open TEST, $test_file or die "$test_file not found";

# Save each line of the input files in an array
@src_lines = <SRC>;
@test_lines = <TEST>;

# Remove the first element of each array, it's just the col label
shift(@src_lines);
shift(@test_lines);

# Parse src data
foreach $src_line(@src_lines) {
	@src_cols = split(/\s+/, $src_line);
	push(@src_labels, @src_cols[0]);
	push(@src_ccn, @src_cols[3]);
	push(@src_ncss, @src_cols[4]);
	push(@src_javadocs, @src_cols[5]);
}

# Parse test data
foreach $test_line(@test_lines) {
	@test_cols = split(/\s+/, $test_line);
	push(@test_ccn, @test_cols[3]);
	push(@test_ncss, @test_cols[4]);
	push(@test_javadocs, @test_cols[5]);
}

# cut the "_src" from the end of the labels
$i = 0;

foreach $src_label(@src_labels) {
	@src_labels[$i] = split(/_src/, @src_labels[$i]);
	$i++;
}

# Close the file handles
close SRC;
close TEST;

########################################
# Print the data for javadoc_histo.txt #
########################################

open JAVADOC_HISTO, ">$dir/javadoc_histo.txt" or die $!;

foreach $src_label(@src_labels) {
	print JAVADOC_HISTO "$src_label\t";
}

print JAVADOC_HISTO "\n";

foreach $src_javadocs(@src_javadocs) {
	print JAVADOC_HISTO "$src_javadocs\t";
}

print JAVADOC_HISTO "\n";

foreach $test_javadocs(@test_javadocs) {
	print JAVADOC_HISTO "$test_javadocs\t";
}

print JAVADOC_HISTO "\n";

close JAVADOC_HISTO;
########################################
########################################

########################################
# Print the data for ncss_histo.txt #
########################################

open NCSS_HISTO, ">$dir/ncss_histo.txt" or die $!;

foreach $src_label(@src_labels) {
	print NCSS_HISTO "$src_label\t";
}

print NCSS_HISTO "\n";

foreach $src_ncss(@src_ncss) {
	print NCSS_HISTO "$src_ncss\t";
}

print NCSS_HISTO "\n";

foreach $test_ncss(@test_ncss) {
	print NCSS_HISTO "$test_ncss\t";
}

print NCSS_HISTO "\n";

close NCSS_HISTO;
########################################
########################################

########################################
# Print the data for ccn_histo.txt #
########################################

open CCN_HISTO, ">$dir/ccn_histo.txt" or die $!;

foreach $src_label(@src_labels) {
	print CCN_HISTO "$src_label\t";
}

print CCN_HISTO "\n";

foreach $src_ccn(@src_ccn) {
	print CCN_HISTO "$src_ccn\t";
}

print CCN_HISTO "\n";

foreach $test_ccn(@test_ccn) {
	print CCN_HISTO "$test_ccn\t";
}

print CCN_HISTO "\n";

close CCN_HISTO;
########################################
########################################
