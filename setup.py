#coding=utf-8
from setuptools import setup, find_packages

_URI = 'https://github.com/luckuan/python'

setup(
    name = 'mlsTools',
    version = '0.1',
    keywords = ('kuan', 'python', 'tools'),
    description = 'mls python 工具类',
    long_description='Visit ' + _URI + ' for details please.',
    license = 'MIT License',
    install_requires = ['simplejson>=1.1'],
    url=_URI,
    author = 'happykuan@126.com',
    author_email = 'happykuan@126.com',
    
    packages = find_packages(),
    platforms = 'any',
)