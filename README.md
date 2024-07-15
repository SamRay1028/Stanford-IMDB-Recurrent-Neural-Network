# Stanford-IMDB-Recurrent-Neural-Network

Description: This is a project that contains the final iteration of a recurrent neural network programmed from scratch and trained on the Stanford large movie review dataset, a dataset that consists of 50,000 movie reviews that either express positive or negative sentiments. The network is trained to identify whether the text from a review expresses either positive or negative sentiment. Root Mean Squared propogation is used to optimize training. Currently, maximum accuracy achieved is around 80%. The software "cleans" each review by getting rid of punctuation besideds periods. It then translates the words of each review into the words expected rating. The reviews are broken up into batches of 100 reviews each. Each sentence of each review is feedforward through the network and after each sentence, backpropagation is performed. 

The weights and biases of the network are only updated after all the reviews of one batch have been fed through the network. Once training is complete the software prompts users to input their own text in order to test the capabilities of the network for themselves. 

This project includes files from the following sources.
1. aclimdb - Originally created by Andrew L. Maas, Raymond E. Daly, Peter T. Pham, Dan Huang, Andrew Y. Ng, and Christopher Potts. Downloaded from https://ai.stanford.edu/~amaas/data/sentiment/

Liscense: This project is licensed under the MIT license - see the LICENSE file for details.

APA citation: Maas, A., Daly, R. E., Pham, P. T., Huang, D., Ng, A. Y., & Potts, C. (2011). Large movie review dataset. In Proceedings of the 49th Annual Meeting of the Association for Computational Linguistics (ACL 2011).
