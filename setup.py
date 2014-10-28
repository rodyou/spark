#coding=utf-8
from setuptools import setup, find_packages

setup(
    name = 'mlsTools',
    version = '0.1',
    keywords = ('kuan', 'python', 'tools'),
    description = 'mls python 工具类',
    license = 'MIT License',
    install_requires = ['simplejson>=1.1'],

    author = 'happykuan@126.com',
    author_email = 'happykuan@126.com',
    
    packages = find_packages(),
    platforms = 'any',
)